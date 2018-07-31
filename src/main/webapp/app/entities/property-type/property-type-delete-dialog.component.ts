import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropertyType } from 'app/shared/model/property-type.model';
import { PropertyTypeService } from './property-type.service';

@Component({
    selector: 'jhi-property-type-delete-dialog',
    templateUrl: './property-type-delete-dialog.component.html'
})
export class PropertyTypeDeleteDialogComponent {
    propertyType: IPropertyType;

    constructor(
        private propertyTypeService: PropertyTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.propertyTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'propertyTypeListModification',
                content: 'Deleted an propertyType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-property-type-delete-popup',
    template: ''
})
export class PropertyTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ propertyType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PropertyTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.propertyType = propertyType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
