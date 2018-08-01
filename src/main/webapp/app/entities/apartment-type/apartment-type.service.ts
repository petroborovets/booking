import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApartmentType } from 'app/shared/model/apartment-type.model';

type EntityResponseType = HttpResponse<IApartmentType>;
type EntityArrayResponseType = HttpResponse<IApartmentType[]>;

@Injectable({ providedIn: 'root' })
export class ApartmentTypeService {
    private resourceUrl = SERVER_API_URL + 'api/apartment-types';

    constructor(private http: HttpClient) {}

    create(apartmentType: IApartmentType): Observable<EntityResponseType> {
        return this.http.post<IApartmentType>(this.resourceUrl, apartmentType, { observe: 'response' });
    }

    update(apartmentType: IApartmentType): Observable<EntityResponseType> {
        return this.http.put<IApartmentType>(this.resourceUrl, apartmentType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IApartmentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IApartmentType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
