import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPropertyType } from 'app/shared/model/property-type.model';

type EntityResponseType = HttpResponse<IPropertyType>;
type EntityArrayResponseType = HttpResponse<IPropertyType[]>;

@Injectable({ providedIn: 'root' })
export class PropertyTypeService {
    private resourceUrl = SERVER_API_URL + 'api/property-types';

    constructor(private http: HttpClient) {}

    create(propertyType: IPropertyType): Observable<EntityResponseType> {
        return this.http.post<IPropertyType>(this.resourceUrl, propertyType, { observe: 'response' });
    }

    update(propertyType: IPropertyType): Observable<EntityResponseType> {
        return this.http.put<IPropertyType>(this.resourceUrl, propertyType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPropertyType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPropertyType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
