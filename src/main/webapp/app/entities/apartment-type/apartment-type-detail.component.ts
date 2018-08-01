import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApartmentType } from 'app/shared/model/apartment-type.model';

@Component({
    selector: 'jhi-apartment-type-detail',
    templateUrl: './apartment-type-detail.component.html'
})
export class ApartmentTypeDetailComponent implements OnInit {
    apartmentType: IApartmentType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apartmentType }) => {
            this.apartmentType = apartmentType;
        });
    }

    previousState() {
        window.history.back();
    }
}
