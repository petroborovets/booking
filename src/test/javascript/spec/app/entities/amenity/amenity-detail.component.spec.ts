/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { AmenityDetailComponent } from 'app/entities/amenity/amenity-detail.component';
import { Amenity } from 'app/shared/model/amenity.model';

describe('Component Tests', () => {
    describe('Amenity Management Detail Component', () => {
        let comp: AmenityDetailComponent;
        let fixture: ComponentFixture<AmenityDetailComponent>;
        const route = ({ data: of({ amenity: new Amenity(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [AmenityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AmenityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AmenityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.amenity).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
