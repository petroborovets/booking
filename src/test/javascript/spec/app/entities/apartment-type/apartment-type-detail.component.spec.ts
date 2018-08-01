/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { ApartmentTypeDetailComponent } from 'app/entities/apartment-type/apartment-type-detail.component';
import { ApartmentType } from 'app/shared/model/apartment-type.model';

describe('Component Tests', () => {
    describe('ApartmentType Management Detail Component', () => {
        let comp: ApartmentTypeDetailComponent;
        let fixture: ComponentFixture<ApartmentTypeDetailComponent>;
        const route = ({ data: of({ apartmentType: new ApartmentType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [ApartmentTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApartmentTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApartmentTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apartmentType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
