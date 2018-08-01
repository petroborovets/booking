import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IApartmentType } from 'app/shared/model/apartment-type.model';
import { ApartmentTypeService } from './apartment-type.service';

@Component({
    selector: 'jhi-apartment-type-update',
    templateUrl: './apartment-type-update.component.html'
})
export class ApartmentTypeUpdateComponent implements OnInit {
    private _apartmentType: IApartmentType;
    isSaving: boolean;

    constructor(private apartmentTypeService: ApartmentTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apartmentType }) => {
            this.apartmentType = apartmentType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.apartmentType.id !== undefined) {
            this.subscribeToSaveResponse(this.apartmentTypeService.update(this.apartmentType));
        } else {
            this.subscribeToSaveResponse(this.apartmentTypeService.create(this.apartmentType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApartmentType>>) {
        result.subscribe((res: HttpResponse<IApartmentType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get apartmentType() {
        return this._apartmentType;
    }

    set apartmentType(apartmentType: IApartmentType) {
        this._apartmentType = apartmentType;
    }
}
