import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPropertyType } from 'app/shared/model/property-type.model';
import { Principal } from 'app/core';
import { PropertyTypeService } from './property-type.service';

@Component({
    selector: 'jhi-property-type',
    templateUrl: './property-type.component.html'
})
export class PropertyTypeComponent implements OnInit, OnDestroy {
    propertyTypes: IPropertyType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private propertyTypeService: PropertyTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.propertyTypeService.query().subscribe(
            (res: HttpResponse<IPropertyType[]>) => {
                this.propertyTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPropertyTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPropertyType) {
        return item.id;
    }

    registerChangeInPropertyTypes() {
        this.eventSubscriber = this.eventManager.subscribe('propertyTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
