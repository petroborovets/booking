/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingTestModule } from '../../../test.module';
import { PropertyTypeComponent } from 'app/entities/property-type/property-type.component';
import { PropertyTypeService } from 'app/entities/property-type/property-type.service';
import { PropertyType } from 'app/shared/model/property-type.model';

describe('Component Tests', () => {
    describe('PropertyType Management Component', () => {
        let comp: PropertyTypeComponent;
        let fixture: ComponentFixture<PropertyTypeComponent>;
        let service: PropertyTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [PropertyTypeComponent],
                providers: []
            })
                .overrideTemplate(PropertyTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PropertyTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PropertyType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.propertyTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
