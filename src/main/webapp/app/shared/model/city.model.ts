export interface ICity {
    id?: number;
    name?: string;
    countryName?: string;
    countryId?: number;
}

export class City implements ICity {
    constructor(public id?: number, public name?: string, public countryName?: string, public countryId?: number) {}
}
