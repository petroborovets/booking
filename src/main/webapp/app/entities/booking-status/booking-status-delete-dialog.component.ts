import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookingStatus } from 'app/shared/model/booking-status.model';
import { BookingStatusService } from './booking-status.service';

@Component({
    selector: 'jhi-booking-status-delete-dialog',
    templateUrl: './booking-status-delete-dialog.component.html'
})
export class BookingStatusDeleteDialogComponent {
    bookingStatus: IBookingStatus;

    constructor(
        private bookingStatusService: BookingStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bookingStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bookingStatusListModification',
                content: 'Deleted an bookingStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-booking-status-delete-popup',
    template: ''
})
export class BookingStatusDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bookingStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BookingStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bookingStatus = bookingStatus;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
