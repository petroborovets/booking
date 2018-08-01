import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAmenity } from 'app/shared/model/amenity.model';
import { AmenityService } from './amenity.service';

@Component({
    selector: 'jhi-amenity-update',
    templateUrl: './amenity-update.component.html'
})
export class AmenityUpdateComponent implements OnInit {
    private _amenity: IAmenity;
    isSaving: boolean;

    constructor(private amenityService: AmenityService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ amenity }) => {
            this.amenity = amenity;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.amenity.id !== undefined) {
            this.subscribeToSaveResponse(this.amenityService.update(this.amenity));
        } else {
            this.subscribeToSaveResponse(this.amenityService.create(this.amenity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAmenity>>) {
        result.subscribe((res: HttpResponse<IAmenity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get amenity() {
        return this._amenity;
    }

    set amenity(amenity: IAmenity) {
        this._amenity = amenity;
    }
}
