/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { PropertyTypeUpdateComponent } from 'app/entities/property-type/property-type-update.component';
import { PropertyTypeService } from 'app/entities/property-type/property-type.service';
import { PropertyType } from 'app/shared/model/property-type.model';

describe('Component Tests', () => {
    describe('PropertyType Management Update Component', () => {
        let comp: PropertyTypeUpdateComponent;
        let fixture: ComponentFixture<PropertyTypeUpdateComponent>;
        let service: PropertyTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookingTestModule],
                declarations: [PropertyTypeUpdateComponent]
            })
                .overrideTemplate(PropertyTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PropertyTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PropertyType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.propertyType = entity;
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
                    const entity = new PropertyType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.propertyType = entity;
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
