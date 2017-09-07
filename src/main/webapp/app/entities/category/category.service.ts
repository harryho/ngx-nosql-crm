import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Category } from './category.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CategoryService {

    private resourceUrl = 'api/categories';

    constructor(private http: Http) { }

    create(category: Category): Observable<Category> {
        const copy = this.convert(category);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(category: Category): Observable<Category> {
        const copy = this.convert(category);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: string): Observable<Category> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(category: Category): Category {
        const copy: Category = Object.assign({}, category);
        return copy;
    }
}
