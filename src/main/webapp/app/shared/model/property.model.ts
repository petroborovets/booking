export interface IProperty {
    id?: number;
    name?: string;
    description?: string;
    contactName?: string;
    contactEmail?: string;
    phoneNumber?: string;
    rating?: number;
    ownerLogin?: string;
    ownerId?: number;
    addressAddressLine1?: string;
    addressId?: number;
    propertyTypeName?: string;
    propertyTypeId?: number;
}

export class Property implements IProperty {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public contactName?: string,
        public contactEmail?: string,
        public phoneNumber?: string,
        public rating?: number,
        public ownerLogin?: string,
        public ownerId?: number,
        public addressAddressLine1?: string,
        public addressId?: number,
        public propertyTypeName?: string,
        public propertyTypeId?: number
    ) {}
}
