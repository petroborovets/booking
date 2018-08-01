import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared';
import {
    ApartmentTypeComponent,
    ApartmentTypeDetailComponent,
    ApartmentTypeUpdateComponent,
    ApartmentTypeDeletePopupComponent,
    ApartmentTypeDeleteDialogComponent,
    apartmentTypeRoute,
    apartmentTypePopupRoute
} from './';

const ENTITY_STATES = [...apartmentTypeRoute, ...apartmentTypePopupRoute];

@NgModule({
    imports: [BookingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApartmentTypeComponent,
        ApartmentTypeDetailComponent,
        ApartmentTypeUpdateComponent,
        ApartmentTypeDeleteDialogComponent,
        ApartmentTypeDeletePopupComponent
    ],
    entryComponents: [
        ApartmentTypeComponent,
        ApartmentTypeUpdateComponent,
        ApartmentTypeDeleteDialogComponent,
        ApartmentTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingApartmentTypeModule {}
