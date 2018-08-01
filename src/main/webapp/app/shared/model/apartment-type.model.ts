export interface IApartmentType {
    id?: number;
    name?: string;
}

export class ApartmentType implements IApartmentType {
    constructor(public id?: number, public name?: string) {}
}
