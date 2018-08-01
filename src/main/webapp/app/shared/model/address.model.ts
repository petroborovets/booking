export interface IAddress {
    id?: number;
    addressLine1?: string;
    addressLine2?: string;
    zip?: string;
    cityName?: string;
    cityId?: number;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public addressLine1?: string,
        public addressLine2?: string,
        public zip?: string,
        public cityName?: string,
        public cityId?: number
    ) {}
}
