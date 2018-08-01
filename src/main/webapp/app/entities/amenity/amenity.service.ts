import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmenity } from 'app/shared/model/amenity.model';

type EntityResponseType = HttpResponse<IAmenity>;
type EntityArrayResponseType = HttpResponse<IAmenity[]>;

@Injectable({ providedIn: 'root' })
export class AmenityService {
    private resourceUrl = SERVER_API_URL + 'api/amenities';

    constructor(private http: HttpClient) {}

    create(amenity: IAmenity): Observable<EntityResponseType> {
        return this.http.post<IAmenity>(this.resourceUrl, amenity, { observe: 'response' });
    }

    update(amenity: IAmenity): Observable<EntityResponseType> {
        return this.http.put<IAmenity>(this.resourceUrl, amenity, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAmenity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAmenity[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
