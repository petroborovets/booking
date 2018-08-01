export interface IBookingStatus {
    id?: number;
    name?: string;
}

export class BookingStatus implements IBookingStatus {
    constructor(public id?: number, public name?: string) {}
}
