import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPropertyType } from 'app/shared/model/property-type.model';
import { PropertyTypeService } from './property-type.service';

@Component({
    selector: 'jhi-property-type-update',
    templateUrl: './property-type-update.component.html'
})
export class PropertyTypeUpdateComponent implements OnInit {
    private _propertyType: IPropertyType;
    isSaving: boolean;

    constructor(private propertyTypeService: PropertyTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ propertyType }) => {
            this.propertyType = propertyType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.propertyType.id !== undefined) {
            this.subscribeToSaveResponse(this.propertyTypeService.update(this.propertyType));
        } else {
            this.subscribeToSaveResponse(this.propertyTypeService.create(this.propertyType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPropertyType>>) {
        result.subscribe((res: HttpResponse<IPropertyType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get propertyType() {
        return this._propertyType;
    }

    set propertyType(propertyType: IPropertyType) {
        this._propertyType = propertyType;
    }
}
