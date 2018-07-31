import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAmenity } from 'app/shared/model/amenity.model';
import { Principal } from 'app/core';
import { AmenityService } from './amenity.service';

@Component({
    selector: 'jhi-amenity',
    templateUrl: './amenity.component.html'
})
export class AmenityComponent implements OnInit, OnDestroy {
    amenities: IAmenity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private amenityService: AmenityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.amenityService.query().subscribe(
            (res: HttpResponse<IAmenity[]>) => {
                this.amenities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAmenities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAmenity) {
        return item.id;
    }

    registerChangeInAmenities() {
        this.eventSubscriber = this.eventManager.subscribe('amenityListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
