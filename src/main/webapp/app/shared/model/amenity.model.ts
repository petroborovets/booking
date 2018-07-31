export interface IAmenity {
    id?: number;
    name?: string;
}

export class Amenity implements IAmenity {
    constructor(public id?: number, public name?: string) {}
}
