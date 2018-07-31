import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared';
import {
    PropertyTypeComponent,
    PropertyTypeDetailComponent,
    PropertyTypeUpdateComponent,
    PropertyTypeDeletePopupComponent,
    PropertyTypeDeleteDialogComponent,
    propertyTypeRoute,
    propertyTypePopupRoute
} from './';

const ENTITY_STATES = [...propertyTypeRoute, ...propertyTypePopupRoute];

@NgModule({
    imports: [BookingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PropertyTypeComponent,
        PropertyTypeDetailComponent,
        PropertyTypeUpdateComponent,
        PropertyTypeDeleteDialogComponent,
        PropertyTypeDeletePopupComponent
    ],
    entryComponents: [
        PropertyTypeComponent,
        PropertyTypeUpdateComponent,
        PropertyTypeDeleteDialogComponent,
        PropertyTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingPropertyTypeModule {}
