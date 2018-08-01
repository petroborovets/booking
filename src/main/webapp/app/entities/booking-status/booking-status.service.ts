import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBookingStatus } from 'app/shared/model/booking-status.model';

type EntityResponseType = HttpResponse<IBookingStatus>;
type EntityArrayResponseType = HttpResponse<IBookingStatus[]>;

@Injectable({ providedIn: 'root' })
export class BookingStatusService {
    private resourceUrl = SERVER_API_URL + 'api/booking-statuses';

    constructor(private http: HttpClient) {}

    create(bookingStatus: IBookingStatus): Observable<EntityResponseType> {
        return this.http.post<IBookingStatus>(this.resourceUrl, bookingStatus, { observe: 'response' });
    }

    update(bookingStatus: IBookingStatus): Observable<EntityResponseType> {
        return this.http.put<IBookingStatus>(this.resourceUrl, bookingStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBookingStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBookingStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
