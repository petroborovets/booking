/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingTestModule } from '../../../test.module';
import { AmenityComponent } from 'app/entities/amenity/amenity.component';
import { AmenityService } from 'app/entities/amenity/amenity.service';
import { Amenity } from 'app/shared/model/amenity.model';

describe('Component Tests', () => {
    describe('Amenity Management Component', () => {
        let comp: AmenityComponent;
        let fixture: ComponentFixture<AmenityComponent>;
        let service: AmenityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [AmenityComponent],
                providers: []
            })
                .overrideTemplate(AmenityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AmenityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmenityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Amenity(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.amenities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
