/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { BookingStatusUpdateComponent } from 'app/entities/booking-status/booking-status-update.component';
import { BookingStatusService } from 'app/entities/booking-status/booking-status.service';
import { BookingStatus } from 'app/shared/model/booking-status.model';

describe('Component Tests', () => {
    describe('BookingStatus Management Update Component', () => {
        let comp: BookingStatusUpdateComponent;
        let fixture: ComponentFixture<BookingStatusUpdateComponent>;
        let service: BookingStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [BookingStatusUpdateComponent]
            })
                .overrideTemplate(BookingStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BookingStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookingStatusService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BookingStatus(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bookingStatus = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BookingStatus();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bookingStatus = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
