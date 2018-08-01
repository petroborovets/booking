import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBookingStatus } from 'app/shared/model/booking-status.model';
import { BookingStatusService } from './booking-status.service';

@Component({
    selector: 'jhi-booking-status-update',
    templateUrl: './booking-status-update.component.html'
})
export class BookingStatusUpdateComponent implements OnInit {
    private _bookingStatus: IBookingStatus;
    isSaving: boolean;

    constructor(private bookingStatusService: BookingStatusService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bookingStatus }) => {
            this.bookingStatus = bookingStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bookingStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.bookingStatusService.update(this.bookingStatus));
        } else {
            this.subscribeToSaveResponse(this.bookingStatusService.create(this.bookingStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBookingStatus>>) {
        result.subscribe((res: HttpResponse<IBookingStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get bookingStatus() {
        return this._bookingStatus;
    }

    set bookingStatus(bookingStatus: IBookingStatus) {
        this._bookingStatus = bookingStatus;
    }
}
