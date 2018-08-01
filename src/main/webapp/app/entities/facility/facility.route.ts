import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Facility } from 'app/shared/model/facility.model';
import { FacilityService } from './facility.service';
import { FacilityComponent } from './facility.component';
import { FacilityDetailComponent } from './facility-detail.component';
import { FacilityUpdateComponent } from './facility-update.component';
import { FacilityDeletePopupComponent } from './facility-delete-dialog.component';
import { IFacility } from 'app/shared/model/facility.model';

@Injectable({ providedIn: 'root' })
export class FacilityResolve implements Resolve<IFacility> {
    constructor(private service: FacilityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((facility: HttpResponse<Facility>) => facility.body));
        }
        return of(new Facility());
    }
}

export const facilityRoute: Routes = [
    {
        path: 'facility',
        component: FacilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.facility.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'facility/:id/view',
        component: FacilityDetailComponent,
        resolve: {
            facility: FacilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.facility.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'facility/new',
        component: FacilityUpdateComponent,
        resolve: {
            facility: FacilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.facility.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'facility/:id/edit',
        component: FacilityUpdateComponent,
        resolve: {
            facility: FacilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.facility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facilityPopupRoute: Routes = [
    {
        path: 'facility/:id/delete',
        component: FacilityDeletePopupComponent,
        resolve: {
            facility: FacilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bookingApp.facility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
