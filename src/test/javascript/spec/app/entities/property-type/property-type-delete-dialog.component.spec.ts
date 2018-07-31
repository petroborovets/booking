/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookingTestModule } from '../../../test.module';
import { PropertyTypeDeleteDialogComponent } from 'app/entities/property-type/property-type-delete-dialog.component';
import { PropertyTypeService } from 'app/entities/property-type/property-type.service';

describe('Component Tests', () => {
    describe('PropertyType Management Delete Component', () => {
        let comp: PropertyTypeDeleteDialogComponent;
        let fixture: ComponentFixture<PropertyTypeDeleteDialogComponent>;
        let service: PropertyTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [PropertyTypeDeleteDialogComponent]
            })
                .overrideTemplate(PropertyTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PropertyTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyTypeService);
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
