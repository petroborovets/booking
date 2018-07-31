export interface IPropertyType {
    id?: number;
    name?: string;
}

export class PropertyType implements IPropertyType {
    constructor(public id?: number, public name?: string) {}
}
