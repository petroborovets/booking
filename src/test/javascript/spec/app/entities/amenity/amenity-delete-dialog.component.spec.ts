/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookingTestModule } from '../../../test.module';
import { AmenityDeleteDialogComponent } from 'app/entities/amenity/amenity-delete-dialog.component';
import { AmenityService } from 'app/entities/amenity/amenity.service';

describe('Component Tests', () => {
    describe('Amenity Management Delete Component', () => {
        let comp: AmenityDeleteDialogComponent;
        let fixture: ComponentFixture<AmenityDeleteDialogComponent>;
        let service: AmenityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [AmenityDeleteDialogComponent]
            })
                .overrideTemplate(AmenityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AmenityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmenityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
