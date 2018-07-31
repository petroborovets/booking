import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PropertyType } from 'app/shared/model/property-type.model';
import { PropertyTypeService } from './property-type.service';
import { PropertyTypeComponent } from './property-type.component';
import { PropertyTypeDetailComponent } from './property-type-detail.component';
import { PropertyTypeUpdateComponent } from './property-type-update.component';
import { PropertyTypeDeletePopupComponent } from './property-type-delete-dialog.component';
import { IPropertyType } from 'app/shared/model/property-type.model';

@Injectable({ providedIn: 'root' })
export class PropertyTypeResolve implements Resolve<IPropertyType> {
    constructor(private service: PropertyTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((propertyType: HttpResponse<PropertyType>) => propertyType.body));
        }
        return of(new PropertyType());
    }
}

export const propertyTypeRoute: Routes = [
    {
        path: 'property-type',
        component: PropertyTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.propertyType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-type/:id/view',
        component: PropertyTypeDetailComponent,
        resolve: {
            propertyType: PropertyTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.propertyType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-type/new',
        component: PropertyTypeUpdateComponent,
        resolve: {
            propertyType: PropertyTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.propertyType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-type/:id/edit',
        component: PropertyTypeUpdateComponent,
        resolve: {
            propertyType: PropertyTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.propertyType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const propertyTypePopupRoute: Routes = [
    {
        path: 'property-type/:id/delete',
        component: PropertyTypeDeletePopupComponent,
        resolve: {
            propertyType: PropertyTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.propertyType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
