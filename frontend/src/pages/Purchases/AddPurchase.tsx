import React, { useState } from "react";
import { Formik, Field, Form } from "formik";
import {
    Button,
    Box,
    TextField,
    Paper,
    Typography,
    Divider,
    Grid2,
    Table,
    Tab,
    TableRow,
    TableBody,
    TableCell,
    tableCellClasses,
    Modal,
    Checkbox,
    Autocomplete,
} from "@mui/material";
import CreatableSelect from "react-select/creatable";
import { useFormik } from "formik";
import CustomReactSelect from "../../components/CustomReactSelect";
import { usePostMasterProductDataMutation } from "../../features/MasterProductService/MasterProductApi";
import { useGetCategoryQuery } from "../../features/CategoryService/CategoryApi";
import {
    useGetProductsQuery,
    usePostProductMutation,
} from "../../features/ProductService/ProductApi";
import { useGetModelsQuery } from "../../features/ModelApi/ModelApi";
import MuiAutoComplete from "../../components/MuiAutoComplete";
import Select from 'react-select'
import CustomDatePicker from "../../components/CustomDatePicker";
import DatePicker from "../../components/DatePicker";
import { Add, CheckBox, PlusOne } from "@mui/icons-material";
import AddVendor from "../Vendor/AddVendor";
import * as Yup from 'yup'
import { useGetVendorListQuery } from "../../features/VendorService/vendorApi";
import { useNavigate } from "react-router-dom";
import { usePostPurchaseMutation } from "../../features/PruchaseApi/PurchaseApi";






// const vendorOptions: Array<{ label: string, value: any }> = [
//     { label: "one", value: "One" },
//     { label: "two", value: "Two" },
//     { label: "three", value: "Three" }
// ]

const consignedOptions: Array<{ label: string, value: any }> = [
    { label: "CHENNAI", value: "CHENNAI" },
    { label: "HYDERABAD", value: "HYDERABAD" },
    { label: "BANGALORE", value: "BANGALORE" }
]

const Calculations = (
    <Table sx={{
        [`& .${tableCellClasses.root}`]: {
            borderBottom: "none"
        }
    }}>
        <TableBody >
            <TableRow sx={{ border: "none" }}>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>Subtotal</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0.00</TableCell>
            </TableRow>
            <TableRow sx={{ border: "none" }}>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>CGST(+)</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>SGST(+)</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>IGST(+)</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>Tax Amount GST(+)</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>Packing(+)</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0.00</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>Freight(+)</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0.00</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>Grand Total</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0.00</TableCell>
            </TableRow>
        </TableBody>
    </Table>
)

const AddPurchase = () => {
    const navigate = useNavigate();
    const [initialValues, setInitialValues] = useState({
        vendorId: null,
        GSTNo: "",
        invoiceNo: "",
        invoiceDate: "",
        consignedTo: null,
        purchaseOrderNo: "",
        purchaseOrderDate: "",
        modeOfDespatch: "",
        transporter: "",
        LR_RR_No: "",
        LR_RR_Date: "",
        packingBox: "",
        weight: "",

        category: null,
        product: null,
        model: null,
        hsnCode: "",
        tax: "",
        quantity: "",
        unitRate: "",
        // masterTax: "",
        taxAmount: "",
        MRPrate: "",
        amount: "",

        packing: "",
        freight: "",
        taxTotal: "",
        paidDate: "",
        paidAmount: "",
    })
    const [openAddVendorModal, setOpenAddVendorModal] = useState<boolean>(false)
    const [isPackingTaxIncluded, setIsPackingTaxIncluded] = useState(false);
    const [isFreightTaxIncluded, setIsFreightTaxIncluded] = useState(false);

    const handlePackingTaxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setIsPackingTaxIncluded(event.target.checked);
        console.log("Packing Tax Included:", event.target.checked);
    };

    const handleFreightTaxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setIsFreightTaxIncluded(event.target.checked);
        console.log("Freight Tax Included:", event.target.checked);
    };



    const {
        data: vendorList,
        error: vendorListError,
        isLoading: vendorListIsLoading,
    } = useGetVendorListQuery({});

    console.log("From purchase", vendorList?.data);


    const {
        data: categories,
        error: categoriesError,
        isLoading: categoriesIsLoading,
    } = useGetCategoryQuery({});
    const {
        data: products,
        error: productsError,
        isLoading: productsIsLoading,
    } = useGetProductsQuery({});
    const {
        data: models,
        error: modelsError,
        isLoading: modelsIsLoading,
    } = useGetModelsQuery({});

    // const vendorOptions = vendorList?.data?.map((vendor:any) => {
    //     return {label:vendor.name, value: vendor.id}
    // })
    const vendorOptions = vendorList?.data?.map((vendor: any) => ({
        value: vendor.id || 1,
        label: `${vendor.title} ${vendor.name[0].toUpperCase() + vendor.name.slice(1).toLowerCase()}`,
    }));

    const [postVendor, { data: postPurchaseData, isLoading: postPurchaseIsLoading, isError: postPurchaseIsError }] = usePostPurchaseMutation()





    console.log(vendorList, postPurchaseData);

    const colourStyles = {
        menuList: (baseStyles: any) => ({
            ...baseStyles,
            background: 'white'
        }),
        option: (baseStyles: any, { isFocused, isSelected }: any) => ({
            ...baseStyles,
            background: isFocused
                ? '#007FFF'
                : isSelected
                    ? '#fff'
                    : undefined,
            color: isFocused
                ? '#fff'
                : isSelected
                    ? '#000'
                    : undefined,
            zIndex: 1
        }),
        menu: (baseStyles: any) => ({
            ...baseStyles,
            zIndex: 100
        }),
    }

    const handleAddVendorClick = () => {
        setOpenAddVendorModal(true)
    }

    const handleClose = () => {
        setOpenAddVendorModal(false)
    }

    const handleClick = (values: any) => {
        console.log("purchase", values);

    }

    const handleChangeConsignedCity = (e: any, values: any) => {
        setInitialValues({ ...values, consignedTo: e.value })
        console.log("logging", { ...values, consignedTo: e.value });

    }


    const isConsignedCitySelected = initialValues?.consignedTo?.length > 0;
    console.log(initialValues?.consignedTo);
    
    console.log(isConsignedCitySelected);

    const handleCloseVendorModal = () => {
        setOpenAddVendorModal(false);
    }

    

    const handleSubmit = (values: any) => {
        console.log(values);
        const data = {
            vendorId: values.vendorId.value,
            orderNo: values.purchaseOrderNo,
            packingBox: values.packingBox,
            gstNo: values.GSTNo,
            orderDate: values.purchaseOrderDate,
            modeOfDispatch: values.modeOfDespatch,
            invoiceNo: values.invoiceNo,
            invoiceDate: values.invoiceDate,
            transporter: values.transporter,
            lrRrNo: values.LR_RR_No,
            lrRrDate: values.LR_RR_Date,
            consignedTo: values.consignedTo.value,
            packing: values.packing,
            freight: values.freight,
            taxTotal: values.taxTotal,
            paidDate: values.paidDate,
            paidAmount: values.paidAmount,
            totalAmount: 2343567, //here you need to add grand total 
            purchaseDetails: [
                {
                    categoryName: values.category.value,
                    productName: values.product.value,
                    modelName: values.model.value,
                    hsnNo: values.hsnCode,
                    quantity: values.quantity,
                    unitRate: values.unitRate,
                    tax: values.tax,
                    taxAmount: values.taxAmount,
                    mrpRate: values.MRPrate,
                    amount: values.amount,
                    includeTax: true
                }
            ]
        }
        postVendor(data).then((res: any) => console.log("post vendor", res))
    }

    return (
        <Box mt={2}>
            <Paper elevation={2} sx={{ borderRadius: 3 }}>
                <Typography sx={{ fontWeight: 700, p: 2 }}>
                    New purchase:
                </Typography>
                <Divider />

                <Formik initialValues={initialValues} onSubmit={handleSubmit}>
                    {({ values, setFieldValue, handleChange, handleBlur }) => (
                        <Form>
                            <Grid2 container px={3} pb={4} columnSpacing={3}>
                                <Grid2 size={4}>
                                    <Box mt={3}>
                                        <Box display={"flex"} justifyContent={"space-between"}>
                                            <Typography my={1}>Choose Vendor</Typography>
                                            <Typography my={1} sx={{ color: "red", cursor: "pointer" }} onClick={handleAddVendorClick}>+ New</Typography>
                                        </Box>
                                        <Autocomplete
                                            disablePortal
                                            options={vendorOptions}
                                            value={values.vendorId}
                                            onChange={(event, newValue) => setFieldValue("vendorId", newValue)}
                                            sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 1px 2px" } }}
                                            // sx={{ width: 300 }}
                                            renderInput={(params: any) => <TextField {...params} placeholder="Choose vendor" name="vendorId"/>}
                                        />
                                        {/* <Select
                                            className="basic-single"
                                            classNamePrefix="select"
                                            // defaultValue={vendorOptions[0].value}
                                            isDisabled={false}
                                            isLoading={false}
                                            isClearable={true}
                                            isRtl={false}
                                            isSearchable={true}
                                            name="vendorId"
                                            options={vendorOptions}
                                            styles={colourStyles}
                                            value={values?.vendorId}
                                        /> */}
                                    </Box>
                                    <Box mt={3}>
                                        <Box display={"flex"} justifyContent={"space-between"}>
                                            <Typography>GST No</Typography>
                                            {/* <Typography sx={{ color: "red", cursor: "pointer" }}>verify</Typography> */}
                                        </Box>
                                        <TextField
                                            name="GSTNo"
                                            type={"text"}
                                            value={values.GSTNo}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            placeholder="GST Number"
                                            fullWidth
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Typography my={1}>Invoice No</Typography>
                                        <TextField
                                            name="invoiceNo"
                                            type={"text"}
                                            value={values.invoiceNo}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            placeholder="Invoice No"
                                            fullWidth
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <DatePicker
                                            placeholder={"Invoice Date"}
                                            label={"Invoice Date"}
                                            name="invoiceDate"
                                            handleOnChange={(date) => setFieldValue("invoiceDate", date)}
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Typography>Consigned To</Typography>
                                        <Autocomplete
                                            disablePortal
                                            options={consignedOptions}
                                            value={values?.consignedTo}
                                            onChange={(event, newValue) => {setFieldValue("consignedTo", newValue); console.log("value consigned", newValue)}}
                                            sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 1px 2px" } }}
                                            // sx={{ width: 300 }}
                                            renderInput={(params: any) => <TextField {...params} placeholder="Choose city" name="consignedTo"/>}
                                        />
                                        {/* <Select
                                            className="basic-single"
                                            classNamePrefix="select"
                                            // defaultValue={consignedOptions[0]?.value}
                                            isDisabled={false}
                                            isLoading={false}
                                            isClearable={false}
                                            isRtl={false}
                                            isSearchable={true}
                                            name="consignedTo"
                                            options={consignedOptions}
                                            onChange={handleChangeConsignedCity}
                                            value={values?.consignedTo}
                                        /> */}
                                    </Box>
                                </Grid2>



                                <Grid2 size={4}>
                                    <Box mt={4}>
                                        <Box>
                                            <Typography my={1}>Purchase Order No</Typography>
                                        </Box>
                                        <TextField
                                            name="purchaseOrderNo"
                                            type={"text"}
                                            value={values.purchaseOrderNo}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Purchase Order No"
                                        />
                                    </Box>
                                    <Box mt={2}>
                                        <DatePicker
                                            placeholder={"Purchase Order Date"}
                                            label={"Purchase Order Date"}
                                            name="purchaseOrderDate"
                                            handleOnChange={(date) => setFieldValue("purchaseOrderDate", date)}
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Box >
                                            <Typography my={1}>Mode Of Despatch</Typography>
                                        </Box>
                                        <TextField
                                            name="modeOfDespatch"
                                            type={"text"}
                                            value={values.modeOfDespatch}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Mode Of Despatch"
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Box >
                                            <Typography my={1}>Transporter</Typography>
                                        </Box>
                                        <TextField
                                            name="transporter"
                                            type={"text"}
                                            value={values.transporter}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Transporter"
                                        />
                                    </Box>


                                </Grid2>

                                <Grid2 size={4}>
                                    <Box mt={4}>
                                        <Box >
                                            <Typography my={1}>LR. RR No</Typography>
                                        </Box>
                                        <TextField
                                            name="LR_RR_No"
                                            type={"text"}
                                            value={values.LR_RR_No}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="LR. RR No"
                                        />
                                    </Box>
                                    <Box mt={2}>
                                        <DatePicker
                                            placeholder={"LR. RR Date"}
                                            label={"LR. RR Date"}
                                            name="LR_RR_Date"
                                            handleOnChange={(date) => setFieldValue("LR_RR_Date", date)}
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Box >
                                            <Typography my={1}>Packing Box</Typography>
                                        </Box>
                                        <TextField
                                            name="packingBox"
                                            type={"number"}
                                            value={values.packingBox}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Packing Box"
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Box >
                                            <Typography my={1}>Weight</Typography>
                                        </Box>
                                        <TextField
                                            name="weight"
                                            type={"text"}
                                            value={values.weight}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Weight"
                                        />
                                    </Box>
                                </Grid2>

                                {/* <Grid2 size={12} display={"flex"} justifyContent={"end"}>
                                    <Box my={2}>
                                        <Button
                                            type="submit"
                                            disabled={loading}
                                            variant="contained"
                                            sx={{ bgcolor: "grey", mr: 3 }}
                                            onClick={handlebackClick}
                                        >
                                            Back
                                        </Button>
                                        <Button
                                            type="submit"
                                            disabled={loading}
                                            variant="contained"
                                            sx={{ bgcolor: "red" }}
                                        >
                                            Submit
                                        </Button>

                                    </Box>
                                </Grid2> */}
                            </Grid2>

                            <Typography p={1} color="grey">Select consigned city to enable these fields:</Typography>

                            <Grid2 container columnSpacing={4} px={4} pb={4} pt={1} sx={{ border: "2px dashed rgba(255, 102, 0, .5)", backgroundColor: "rgba(255, 102, 0, .1)" }}>
                                <Grid2 size={4}>
                                    <Box >
                                        <Typography my={1}>Category</Typography>

                                        <CustomReactSelect
                                            setFieldValue={setFieldValue}
                                            setFieldValueLabel={"category"}
                                            options={
                                                categories
                                                    ? categories.map((category: any) => ({
                                                        label: category.name,
                                                        value: category.id,
                                                    }))
                                                    : []
                                            }
                                            placeholder={
                                                categoriesIsLoading ? "Loading Categories..." : "Select Category"
                                            }
                                            isDisabled={values.consignedTo !== null ? false : true}
                                            isClearable={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={4}>
                                    <Box >
                                        <Typography my={1}>Product</Typography>

                                        <CustomReactSelect
                                            setFieldValue={setFieldValue}
                                            setFieldValueLabel={"product"}
                                            options={
                                                products
                                                    ? products.map((product: any) => ({
                                                        label: product.name,
                                                        value: product.id,
                                                    }))
                                                    : []
                                            }
                                            placeholder={
                                                productsIsLoading ? "Loading Products..." : "Select Product"
                                            }
                                            isDisabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={4}>
                                    <Box >
                                        <Typography my={1}>Model</Typography>

                                        <CustomReactSelect
                                            setFieldValue={setFieldValue}
                                            setFieldValueLabel={"model"}
                                            options={
                                                models
                                                    ? models.map((model: any) => ({
                                                        label: model.name,
                                                        value: model.id,
                                                    }))
                                                    : []
                                            }
                                            placeholder={
                                                modelsIsLoading ? "Loading Models..." : "Select Model"
                                            }
                                            isDisabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={4}>
                                    <Box>
                                        <Typography my={1}>HSN Code</Typography>
                                        <TextField
                                            sx={{ bgcolor: values.consignedTo !== null ?  "#fff" :"#F2F2F2" }}
                                            name="hsnCode"
                                            type={"text"}
                                            value={values.hsnCode}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="HSN Code"
                                            disabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>

                                <Grid2 size={4}>
                                    <Box>
                                        <Typography my={1}>Quantity</Typography>
                                        <TextField
                                            sx={{ bgcolor: values.consignedTo !== null ? "#fff" :"#F2F2F2" }}
                                            name="quantity"
                                            type={"text"}
                                            value={values.quantity}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Quantity"
                                            disabled={values.consignedTo !== null ? false : true}
                                        />
                                        <Box display={"flex"} alignItems={"center"}>

                                            <Add sx={{ fontSize: 17, cursor: "pointer" }} />
                                            <Box sx={{ cursor: "pointer" }}>
                                                serials
                                            </Box>
                                        </Box>

                                    </Box>
                                </Grid2>
                                <Grid2 size={4}>
                                    <Box>
                                        <Typography my={1}>Unit Rate</Typography>
                                        <TextField
                                            sx={{ bgcolor: values.consignedTo !== null ? "#fff"  : "#F2F2F2" }}
                                            name="unitRate"
                                            type={"text"}
                                            value={values.unitRate}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Unit Rate"
                                            disabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={3} mt={1}>
                                    <Box>
                                        <Typography my={1}>Tax</Typography>
                                        <TextField
                                            sx={{ bgcolor:values.consignedTo !== null ? "#fff"  : "#F2F2F2" }}
                                            name="tax"
                                            type={"text"}
                                            value={values.tax}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Tax"
                                            disabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={3} mt={1}>
                                    <Box>
                                        <Typography my={1}>Tax Amount</Typography>
                                        <TextField
                                            sx={{ bgcolor: values.consignedTo !== null ? "#fff"  : "#F2F2F2"  }}
                                            name="taxAmount"
                                            type={"text"}
                                            value={values.taxAmount}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Tax Amount"
                                            disabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={3} mt={1}>
                                    <Box>
                                        <Typography my={1}>MRP Rate</Typography>
                                        <TextField
                                            sx={{ bgcolor: values.consignedTo !== null ? "#fff" :  "#F2F2F2"  }}
                                            name="MRPrate"
                                            type={"text"}
                                            value={values.MRPrate}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="MRP Rate"
                                            disabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={3} mt={1}>
                                    <Box>
                                        <Typography my={1}>Amount</Typography>
                                        <TextField
                                            sx={{ bgcolor: values.consignedTo !== null ? "#fff" : "#F2F2F2"}}
                                            name="amount"
                                            type={"text"}
                                            value={values.amount}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Amount"
                                            disabled={values.consignedTo !== null ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                            </Grid2>

                            <Grid2 container spacing={3} px={4} pb={3}>
                                <Grid2 size={4}>
                                    <Box mt={3}>
                                        <Box display={"flex"} justifyContent={"space-between"}>
                                            <Typography my={1}>Packing</Typography>
                                            <Typography my={1} sx={{ color: "red", cursor: "pointer", display: "flex", alignItems: "center" }}><Checkbox sx={{ mr: 1 }} checked={isPackingTaxIncluded}
                                                onChange={handlePackingTaxChange} />Include Tax 18 %</Typography>
                                        </Box>
                                        <TextField
                                            name="packing"
                                            type={"text"}
                                            value={values.packing}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Packing"
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={4}>
                                    <Box mt={3}>
                                        <Box display={"flex"} justifyContent={"space-between"}>
                                            <Typography my={1}>Freight</Typography>
                                            <Typography my={1} sx={{ color: "red", cursor: "pointer", display: "flex", alignItems: "center" }}><Checkbox sx={{ mr: 1 }} checked={isFreightTaxIncluded}
                                                onChange={handleFreightTaxChange} />Include Tax 18 %</Typography>
                                        </Box>
                                        <TextField
                                            name="freight"
                                            type={"text"}
                                            value={values.freight}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Freight"
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={4}>
                                    <Box mt={4}>
                                        <Typography my={1}>Tax Total</Typography>
                                        <TextField
                                            name="taxTotal"
                                            type={"text"}
                                            value={values.taxTotal}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Tax Total"
                                        />
                                    </Box>
                                </Grid2>
                            </Grid2>
                            <Divider variant="middle" component="li" sx={{ listStyle: "none" }} />
                            <Box>
                                <Grid2 container spacing={3} px={4} pb={2}>
                                    <Grid2 size={3} display={"flex"} alignItems={"end"}>
                                        <Button variant="contained" type="submit" onClick={handleClick}>Purchase</Button>
                                        <Button variant="contained" type="button" sx={{ ml: 2, bgcolor: "grey" }} onClick={() => navigate(-1)}>Back</Button>
                                    </Grid2>
                                    <Grid2 size={4}>
                                        <Box mt={3}>
                                            <DatePicker
                                                placeholder={"Paid Date"}
                                                label={"Paid Date"}
                                                name="paidDate"
                                                handleOnChange={(date) => setFieldValue("paidDate", date)}
                                            />
                                        </Box>
                                        <Box>
                                            <Typography my={1}>Paid Amount</Typography>
                                            <TextField
                                                name="paidAmount"
                                                type={"text"}
                                                value={values.paidAmount}
                                                onBlur={handleBlur}
                                                onChange={handleChange}
                                                size="small"
                                                fullWidth
                                                placeholder="Paid Amount"
                                            />
                                        </Box>
                                    </Grid2>
                                    <Grid2 size={5} >
                                        {
                                            Calculations
                                        }

                                    </Grid2>

                                </Grid2>
                            </Box>
                        </Form>
                    )}
                </Formik>
            </Paper>
            <Modal open={openAddVendorModal} onClose={handleClose}>
                <AddVendor handleClose={handleClose} handleCloseVendorModal={handleCloseVendorModal} />
            </Modal>
        </Box>
    )
}

export default AddPurchase
