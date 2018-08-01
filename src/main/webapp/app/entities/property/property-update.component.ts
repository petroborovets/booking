import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from './property.service';
import { IUser, UserService } from 'app/core';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';
import { IPropertyType } from 'app/shared/model/property-type.model';
import { PropertyTypeService } from 'app/entities/property-type';

@Component({
    selector: 'jhi-property-update',
    templateUrl: './property-update.component.html'
})
export class PropertyUpdateComponent implements OnInit {
    private _property: IProperty;
    isSaving: boolean;

    users: IUser[];

    addresses: IAddress[];

    propertytypes: IPropertyType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private propertyService: PropertyService,
        private userService: UserService,
        private addressService: AddressService,
        private propertyTypeService: PropertyTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ property }) => {
            this.property = property;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.addressService.query().subscribe(
            (res: HttpResponse<IAddress[]>) => {
                this.addresses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.propertyTypeService.query().subscribe(
            (res: HttpResponse<IPropertyType[]>) => {
                this.propertytypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.property.id !== undefined) {
            this.subscribeToSaveResponse(this.propertyService.update(this.property));
        } else {
            this.subscribeToSaveResponse(this.propertyService.create(this.property));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProperty>>) {
        result.subscribe((res: HttpResponse<IProperty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackAddressById(index: number, item: IAddress) {
        return item.id;
    }

    trackPropertyTypeById(index: number, item: IPropertyType) {
        return item.id;
    }
    get property() {
        return this._property;
    }

    set property(property: IProperty) {
        this._property = property;
    }
}
