export interface province {
  acronym?: string;
  name?: string;
  regioId?:number;
}

export interface personalData {
  id?:number ;
  name?: string
  surname?:string;
  dateOfBirth?:string;
  phoneNumber?:string;
  city?:string;
  cap?:string;
  province? : province;
  insertionDate?:string;
  lastModified?:string;
}
export interface cartProduct
{  cartId?: number,
   productId?: number,
   productName?: string,
    quantity?: number,
    price?: number
  }
