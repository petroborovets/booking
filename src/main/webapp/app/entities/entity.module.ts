import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BookingAmenityModule } from './amenity/amenity.module';
import { BookingCountryModule } from './country/country.module';
import { BookingCityModule } from './city/city.module';
import { BookingAddressModule } from './address/address.module';
import { BookingFacilityModule } from './facility/facility.module';
import { BookingPropertyTypeModule } from './property-type/property-type.module';
import { BookingApartmentTypeModule } from './apartment-type/apartment-type.module';
import { BookingBookingStatusModule } from './booking-status/booking-status.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BookingAmenityModule,
        BookingCountryModule,
        BookingCityModule,
        BookingAddressModule,
        BookingFacilityModule,
        BookingPropertyTypeModule,
        BookingApartmentTypeModule,
        BookingBookingStatusModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingEntityModule {}
