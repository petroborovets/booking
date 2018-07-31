import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmenity } from 'app/shared/model/amenity.model';

@Component({
    selector: 'jhi-amenity-detail',
    templateUrl: './amenity-detail.component.html'
})
export class AmenityDetailComponent implements OnInit {
    amenity: IAmenity;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ amenity }) => {
            this.amenity = amenity;
        });
    }

    previousState() {
        window.history.back();
    }
}
