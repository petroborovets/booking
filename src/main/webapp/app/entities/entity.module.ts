import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BookingAmenityModule } from './amenity/amenity.module';
import { BookingCountryModule } from './country/country.module';
import { BookingFacilityModule } from './facility/facility.module';
import { BookingPropertyTypeModule } from './property-type/property-type.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BookingAmenityModule,
        BookingCountryModule,
        BookingFacilityModule,
        BookingPropertyTypeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingEntityModule {}
