import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApartmentType } from 'app/shared/model/apartment-type.model';
import { ApartmentTypeService } from './apartment-type.service';
import { ApartmentTypeComponent } from './apartment-type.component';
import { ApartmentTypeDetailComponent } from './apartment-type-detail.component';
import { ApartmentTypeUpdateComponent } from './apartment-type-update.component';
import { ApartmentTypeDeletePopupComponent } from './apartment-type-delete-dialog.component';
import { IApartmentType } from 'app/shared/model/apartment-type.model';

@Injectable({ providedIn: 'root' })
export class ApartmentTypeResolve implements Resolve<IApartmentType> {
    constructor(private service: ApartmentTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((apartmentType: HttpResponse<ApartmentType>) => apartmentType.body));
        }
        return of(new ApartmentType());
    }
}

export const apartmentTypeRoute: Routes = [
    {
        path: 'apartment-type',
        component: ApartmentTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bookingApp.apartmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apartment-type/:id/view',
        component: ApartmentTypeDetailComponent,
        resolve: {
            apartmentType: ApartmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.apartmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apartment-type/new',
        component: ApartmentTypeUpdateComponent,
        resolve: {
            apartmentType: ApartmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.apartmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apartment-type/:id/edit',
        component: ApartmentTypeUpdateComponent,
        resolve: {
            apartmentType: ApartmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.apartmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apartmentTypePopupRoute: Routes = [
    {
        path: 'apartment-type/:id/delete',
        component: ApartmentTypeDeletePopupComponent,
        resolve: {
            apartmentType: ApartmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.apartmentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
