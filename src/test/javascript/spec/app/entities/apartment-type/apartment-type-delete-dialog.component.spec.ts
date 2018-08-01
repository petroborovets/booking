/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookingTestModule } from '../../../test.module';
import { ApartmentTypeDeleteDialogComponent } from 'app/entities/apartment-type/apartment-type-delete-dialog.component';
import { ApartmentTypeService } from 'app/entities/apartment-type/apartment-type.service';

describe('Component Tests', () => {
    describe('ApartmentType Management Delete Component', () => {
        let comp: ApartmentTypeDeleteDialogComponent;
        let fixture: ComponentFixture<ApartmentTypeDeleteDialogComponent>;
        let service: ApartmentTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [ApartmentTypeDeleteDialogComponent]
            })
                .overrideTemplate(ApartmentTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApartmentTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApartmentTypeService);
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
