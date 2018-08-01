import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBookingStatus } from 'app/shared/model/booking-status.model';
import { Principal } from 'app/core';
import { BookingStatusService } from './booking-status.service';

@Component({
    selector: 'jhi-booking-status',
    templateUrl: './booking-status.component.html'
})
export class BookingStatusComponent implements OnInit, OnDestroy {
    bookingStatuses: IBookingStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bookingStatusService: BookingStatusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bookingStatusService.query().subscribe(
            (res: HttpResponse<IBookingStatus[]>) => {
                this.bookingStatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBookingStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBookingStatus) {
        return item.id;
    }

    registerChangeInBookingStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('bookingStatusListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
