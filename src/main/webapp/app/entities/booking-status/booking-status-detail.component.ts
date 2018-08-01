import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookingStatus } from 'app/shared/model/booking-status.model';

@Component({
    selector: 'jhi-booking-status-detail',
    templateUrl: './booking-status-detail.component.html'
})
export class BookingStatusDetailComponent implements OnInit {
    bookingStatus: IBookingStatus;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bookingStatus }) => {
            this.bookingStatus = bookingStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
