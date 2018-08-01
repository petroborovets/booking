/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { ApartmentTypeUpdateComponent } from 'app/entities/apartment-type/apartment-type-update.component';
import { ApartmentTypeService } from 'app/entities/apartment-type/apartment-type.service';
import { ApartmentType } from 'app/shared/model/apartment-type.model';

describe('Component Tests', () => {
    describe('ApartmentType Management Update Component', () => {
        let comp: ApartmentTypeUpdateComponent;
        let fixture: ComponentFixture<ApartmentTypeUpdateComponent>;
        let service: ApartmentTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [ApartmentTypeUpdateComponent]
            })
                .overrideTemplate(ApartmentTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApartmentTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApartmentTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ApartmentType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.apartmentType = entity;
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
                    const entity = new ApartmentType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.apartmentType = entity;
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
