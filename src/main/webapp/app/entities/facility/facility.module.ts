import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared';
import {
    FacilityComponent,
    FacilityDetailComponent,
    FacilityUpdateComponent,
    FacilityDeletePopupComponent,
    FacilityDeleteDialogComponent,
    facilityRoute,
    facilityPopupRoute
} from './';

const ENTITY_STATES = [...facilityRoute, ...facilityPopupRoute];

@NgModule({
    imports: [BookingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FacilityComponent,
        FacilityDetailComponent,
        FacilityUpdateComponent,
        FacilityDeleteDialogComponent,
        FacilityDeletePopupComponent
    ],
    entryComponents: [FacilityComponent, FacilityUpdateComponent, FacilityDeleteDialogComponent, FacilityDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingFacilityModule {}
