import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPropertyType } from 'app/shared/model/property-type.model';

@Component({
    selector: 'jhi-property-type-detail',
    templateUrl: './property-type-detail.component.html'
})
export class PropertyTypeDetailComponent implements OnInit {
    propertyType: IPropertyType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ propertyType }) => {
            this.propertyType = propertyType;
        });
    }

    previousState() {
        window.history.back();
    }
}
