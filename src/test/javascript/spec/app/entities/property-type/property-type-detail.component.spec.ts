/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { PropertyTypeDetailComponent } from 'app/entities/property-type/property-type-detail.component';
import { PropertyType } from 'app/shared/model/property-type.model';

describe('Component Tests', () => {
    describe('PropertyType Management Detail Component', () => {
        let comp: PropertyTypeDetailComponent;
        let fixture: ComponentFixture<PropertyTypeDetailComponent>;
        const route = ({ data: of({ propertyType: new PropertyType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [PropertyTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PropertyTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PropertyTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.propertyType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
