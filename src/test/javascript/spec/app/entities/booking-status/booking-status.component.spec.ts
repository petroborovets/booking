/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingTestModule } from '../../../test.module';
import { BookingStatusComponent } from 'app/entities/booking-status/booking-status.component';
import { BookingStatusService } from 'app/entities/booking-status/booking-status.service';
import { BookingStatus } from 'app/shared/model/booking-status.model';

describe('Component Tests', () => {
    describe('BookingStatus Management Component', () => {
        let comp: BookingStatusComponent;
        let fixture: ComponentFixture<BookingStatusComponent>;
        let service: BookingStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [BookingStatusComponent],
                providers: []
            })
                .overrideTemplate(BookingStatusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BookingStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookingStatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BookingStatus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bookingStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
