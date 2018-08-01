import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BookingStatus } from 'app/shared/model/booking-status.model';
import { BookingStatusService } from './booking-status.service';
import { BookingStatusComponent } from './booking-status.component';
import { BookingStatusDetailComponent } from './booking-status-detail.component';
import { BookingStatusUpdateComponent } from './booking-status-update.component';
import { BookingStatusDeletePopupComponent } from './booking-status-delete-dialog.component';
import { IBookingStatus } from 'app/shared/model/booking-status.model';

@Injectable({ providedIn: 'root' })
export class BookingStatusResolve implements Resolve<IBookingStatus> {
    constructor(private service: BookingStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bookingStatus: HttpResponse<BookingStatus>) => bookingStatus.body));
        }
        return of(new BookingStatus());
    }
}

export const bookingStatusRoute: Routes = [
    {
        path: 'booking-status',
        component: BookingStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.bookingStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'booking-status/:id/view',
        component: BookingStatusDetailComponent,
        resolve: {
            bookingStatus: BookingStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.bookingStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'booking-status/new',
        component: BookingStatusUpdateComponent,
        resolve: {
            bookingStatus: BookingStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.bookingStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'booking-status/:id/edit',
        component: BookingStatusUpdateComponent,
        resolve: {
            bookingStatus: BookingStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.bookingStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bookingStatusPopupRoute: Routes = [
    {
        path: 'booking-status/:id/delete',
        component: BookingStatusDeletePopupComponent,
        resolve: {
            bookingStatus: BookingStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.bookingStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
