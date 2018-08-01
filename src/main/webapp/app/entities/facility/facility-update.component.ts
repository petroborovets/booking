import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from './facility.service';

@Component({
    selector: 'jhi-facility-update',
    templateUrl: './facility-update.component.html'
})
export class FacilityUpdateComponent implements OnInit {
    private _facility: IFacility;
    isSaving: boolean;

    constructor(private facilityService: FacilityService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ facility }) => {
            this.facility = facility;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.facility.id !== undefined) {
            this.subscribeToSaveResponse(this.facilityService.update(this.facility));
        } else {
            this.subscribeToSaveResponse(this.facilityService.create(this.facility));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFacility>>) {
        result.subscribe((res: HttpResponse<IFacility>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get facility() {
        return this._facility;
    }

    set facility(facility: IFacility) {
        this._facility = facility;
    }
}
