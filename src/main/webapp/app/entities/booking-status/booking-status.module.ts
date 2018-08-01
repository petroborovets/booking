import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared';
import {
    BookingStatusComponent,
    BookingStatusDetailComponent,
    BookingStatusUpdateComponent,
    BookingStatusDeletePopupComponent,
    BookingStatusDeleteDialogComponent,
    bookingStatusRoute,
    bookingStatusPopupRoute
} from './';

const ENTITY_STATES = [...bookingStatusRoute, ...bookingStatusPopupRoute];

@NgModule({
    imports: [BookingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BookingStatusComponent,
        BookingStatusDetailComponent,
        BookingStatusUpdateComponent,
        BookingStatusDeleteDialogComponent,
        BookingStatusDeletePopupComponent
    ],
    entryComponents: [
        BookingStatusComponent,
        BookingStatusUpdateComponent,
        BookingStatusDeleteDialogComponent,
        BookingStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingBookingStatusModule {}
