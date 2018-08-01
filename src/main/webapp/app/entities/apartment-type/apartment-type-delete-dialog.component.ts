import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApartmentType } from 'app/shared/model/apartment-type.model';
import { ApartmentTypeService } from './apartment-type.service';

@Component({
    selector: 'jhi-apartment-type-delete-dialog',
    templateUrl: './apartment-type-delete-dialog.component.html'
})
export class ApartmentTypeDeleteDialogComponent {
    apartmentType: IApartmentType;

    constructor(
        private apartmentTypeService: ApartmentTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apartmentTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apartmentTypeListModification',
                content: 'Deleted an apartmentType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-apartment-type-delete-popup',
    template: ''
})
export class ApartmentTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apartmentType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApartmentTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apartmentType = apartmentType;
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
