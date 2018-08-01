/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { BookingStatusDetailComponent } from 'app/entities/booking-status/booking-status-detail.component';
import { BookingStatus } from 'app/shared/model/booking-status.model';

describe('Component Tests', () => {
    describe('BookingStatus Management Detail Component', () => {
        let comp: BookingStatusDetailComponent;
        let fixture: ComponentFixture<BookingStatusDetailComponent>;
        const route = ({ data: of({ bookingStatus: new BookingStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [BookingStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BookingStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BookingStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bookingStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
