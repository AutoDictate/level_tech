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
    FormControlLabel,
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
import FileUploadIcon from '@mui/icons-material/FileUpload';
import AddCustomer from "../Customer/AddCustomer";




const handleSubmit = (values: any) => {
    console.log(values);
}

const vendorOptions: Array<{ label: string, value: any }> = [
    { label: "one", value: "One" },
    { label: "two", value: "Two" },
    { label: "three", value: "Three" }
]

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
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
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
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>Transport(+)</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ fontWeight: 600, fontSize: 18 }}>Grand Total</TableCell>
                <TableCell sx={{ display: "flex", justifyContent: "end", fontWeight: 600, fontSize: 18 }}>0</TableCell>
            </TableRow>
        </TableBody>
    </Table>
)

const AddPurchase = () => {
    const navigate = useNavigate();
    const [initialValues, setInitialValues] = useState({
        customerId: [],
        gstNo: "",
        billDate: "",
        invoiceNo: "",
        year: "",
        deliveryDate: "",
        deliveryNo: "",
        purchaseOrderNo: "",
        reverseCharge: "",
        dispatchCity: [],
        transporter: "",
        paymentMode: "",
        paymentStatus: "",
        sameShipping: "",

        category: null,
        product: null,
        model: null,
        hsnCode: "",
        quantity: "",
        MRPrate: "",
        tax: "",
        taxAmount: "",
        amount: "",
        serviceRemarks: "",

        transportCharge: "",
        packingCharge: "",
        taxTotal: "",
        paidDate: "",
        paidAmount: "",

        Subtotal: "",
        Cgst: "",
        Sgst: "",
        Igst: "",
        TaxAmountGst: "",
        Packing: "",
        Transport: "",
        GrandTotal: "",
    })
    const [openAddVendorModal, setOpenAddVendorModal] = useState<boolean>(false)

    const {
        data: vendorList,
        error: vendorListError,
        isLoading: vendorListIsLoading,
    } = useGetVendorListQuery({});

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



    console.log(vendorList);

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

    const handleAddCustomerClick = () => {
        setOpenAddVendorModal(true)
    }

    const handleClose = () => {
        setOpenAddVendorModal(false)
    }

    const handleClick = (values: any) => {
        console.log("purchase", values);

    }

    const handleChangeDispatchCity = (e: any, values: any) => {
        setInitialValues({ ...values, dispatchCity: e.value })
        console.log("logging", { ...values, consignedTo: e.value });

    }


    const isConsignedCitySelected = initialValues.dispatchCity.length > 0;
    console.log(isConsignedCitySelected);

    const YES_OR_NO_OPTIONS = ["Yes", "No"]
    const paymentOptions = ["Cash", "Cheque", "NEFT"]
    const paymentStatusOptions = ["Received", "Part Payment", "Not Yet"]


    return (
        <Box mt={2}>
            <Paper elevation={2} sx={{ borderRadius: 3 }}>
                <Box display={"flex"} justifyContent={"space-between"} alignItems={"center"}>
                    <Typography sx={{ fontWeight: 700, p: 2 }}>
                        New Sale:
                    </Typography>
                    <Button variant="contained" type="button" sx={{ mr: 2, bgcolor: "grey", height: 30 }} onClick={() => navigate(-1)}>Back</Button>

                </Box>

                <Divider />

                <Formik initialValues={initialValues} onSubmit={handleSubmit}>
                    {({ values, setFieldValue, handleChange, handleBlur }) => (
                        <Form>
                            <Grid2 container px={3} pb={4} columnSpacing={3}>
                                <Grid2 size={6}>
                                    <Box mt={3}>
                                        <Box display={"flex"} justifyContent={"space-between"}>
                                            <Typography my={1}>Choose Customer</Typography>
                                            <Typography my={1} sx={{ color: "red", cursor: "pointer" }} onClick={handleAddCustomerClick}>+ New</Typography>
                                        </Box>
                                        <Select
                                            className="basic-single"
                                            classNamePrefix="select"
                                            isDisabled={false}
                                            isLoading={false}
                                            isClearable={true}
                                            isRtl={false}
                                            isSearchable={true}
                                            name="customerId"
                                            options={vendorList?.data}
                                            styles={colourStyles}
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Box my={1}>
                                            <Typography>GST No</Typography>
                                        </Box>
                                        <TextField
                                            name="gstNo"
                                            type={"text"}
                                            value={values.gstNo}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            placeholder="GST Number"
                                            fullWidth
                                        />
                                    </Box>

                                    <Box mt={3}>
                                        <DatePicker
                                            placeholder={"Bill Date"}
                                            label={"Bill Date"}
                                            name="billDate"
                                            handleOnChange={(date) => setFieldValue("billDate", date)}
                                        />
                                    </Box>
                                    <Grid2 container columnSpacing={2}>
                                        <Grid2 size={6} >
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
                                        </Grid2>
                                        <Grid2 size={6}>
                                            <Box mt={3}>
                                                <Typography my={1}>Year</Typography>
                                                <Select
                                                    className="basic-single"
                                                    classNamePrefix="select"
                                                    defaultValue={vendorOptions[0].value}
                                                    isDisabled={false}
                                                    isLoading={false}
                                                    isClearable={false}
                                                    isRtl={false}
                                                    isSearchable={true}
                                                    name="dispatchCity"
                                                    options={consignedOptions}
                                                    onChange={handleChangeDispatchCity}
                                                />
                                            </Box>
                                        </Grid2>
                                    </Grid2>
                                    <Box mt={3}>
                                        <DatePicker
                                            placeholder={"Delivery Date"}
                                            label={"Delivery Date"}
                                            name="deliveryDate"
                                            handleOnChange={(date) => setFieldValue("deliveryDate", date)}
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Box>
                                            <Typography my={1}>Delivery No</Typography>
                                        </Box>
                                        <TextField
                                            name="deliveryNo"
                                            type={"text"}
                                            value={values.deliveryNo}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Delivery No"
                                        />
                                    </Box>
                                    <Box mt={3}>
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
                                </Grid2>

                                <Grid2 size={6}>
                                    <Box mt={4}>
                                        <Typography my={1}>Reverse Charge (Y/N)</Typography>
                                        <Autocomplete
                                            disablePortal
                                            options={YES_OR_NO_OPTIONS}
                                            value={values.customerId}
                                            onChange={(event: any, newValue: any) => setFieldValue("vendorId", newValue)}
                                            sx={{ "& .MuiOutlinedInput-root": { padding: "0px 2px 0px 2px" } }}
                                            renderInput={(params: any) => <TextField {...params} placeholder="Choose vendor" name="vendorId" />}
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Typography my={1}>Despatch City</Typography>
                                        <Autocomplete
                                            disablePortal
                                            options={consignedOptions}
                                            value={values?.dispatchCity}
                                            onChange={(event, newValue) => { setFieldValue("consignedTo", newValue); console.log("value consigned", newValue) }}
                                            sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 1px 2px" } }}
                                            renderInput={(params: any) => <TextField {...params} placeholder="Choose city" name="consignedTo" />}
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
                                    <Box mt={3}>
                                        <Typography my={1}>Payment Mode</Typography>
                                        <Autocomplete
                                            disablePortal
                                            options={paymentOptions}
                                            value={values.customerId}
                                            onChange={(event: any, newValue: any) => setFieldValue("vendorId", newValue)}
                                            sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 2px 2px" } }}
                                            renderInput={(params: any) => <TextField {...params} placeholder="Choose vendor" name="vendorId" />}
                                        />
                                    </Box>
                                    <Box mt={3}>

                                        <Typography my={1}>Payment</Typography>
                                        <Autocomplete
                                            disablePortal
                                            options={paymentStatusOptions}
                                            value={values.customerId}
                                            onChange={(event: any, newValue: any) => setFieldValue("vendorId", newValue)}
                                            sx={{ "& .MuiOutlinedInput-root": { padding: "0px 2px 2px 2px" } }}
                                            renderInput={(params: any) => <TextField {...params} placeholder="Choose vendor" name="vendorId" />}
                                        />
                                    </Box>
                                    <Box mt={3}>
                                        <Typography my={1}>Same Shipping</Typography>
                                        <Autocomplete
                                            disablePortal
                                            options={YES_OR_NO_OPTIONS}
                                            value={values.customerId}
                                            onChange={(event: any, newValue: any) => setFieldValue("vendorId", newValue)}
                                            sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 2px 2px" } }}
                                            renderInput={(params: any) => <TextField {...params} placeholder="Choose vendor" name="vendorId" />}
                                        />
                                    </Box>
                                    <Box mt={2}>
                                        <Button variant="contained" fullWidth startIcon={<FileUploadIcon />}>PURCHASES ORDER</Button>
                                    </Box>

                                </Grid2>
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
                                            isDisabled={isConsignedCitySelected ? false : true}
                                            isClearable={isConsignedCitySelected ? false : true}
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
                                            isDisabled={isConsignedCitySelected ? false : true}
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
                                            isDisabled={isConsignedCitySelected ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={4}>
                                    <Box>
                                        <Typography my={1}>HSN Code</Typography>
                                        <TextField
                                            sx={{ bgcolor: isConsignedCitySelected === false ? "#F2F2F2" : "#fff" }}
                                            name="hsnCode"
                                            type={"text"}
                                            value={values.hsnCode}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="HSN Code"
                                            disabled={isConsignedCitySelected ? false : true}
                                        />
                                    </Box>
                                </Grid2>

                                <Grid2 size={4}>
                                    <Box>
                                        <Typography my={1}>Quantity</Typography>
                                        <TextField
                                            sx={{ bgcolor: isConsignedCitySelected === false ? "#F2F2F2" : "#fff" }}
                                            name="quantity"
                                            type={"text"}
                                            value={values.quantity}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Quantity"
                                            disabled={isConsignedCitySelected ? false : true}
                                        />
                                        <Box display={"flex"} alignItems={"center"}>

                                            <Add sx={{ fontSize: 17, cursor: "pointer" }} />
                                            <Box sx={{ cursor: "pointer" }}>
                                                serials
                                            </Box>
                                        </Box>

                                    </Box>
                                </Grid2>
                                <Grid2 size={2}>
                                    <Box>
                                        <Typography my={1}>MRP Rate</Typography>
                                        <TextField
                                            sx={{ bgcolor: isConsignedCitySelected === false ? "#F2F2F2" : "#fff" }}
                                            name="MRPrate"
                                            type={"text"}
                                            value={values.MRPrate}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="MRP Rate"
                                            disabled={isConsignedCitySelected ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={2}>
                                    <Box>
                                        <Typography my={1}>Tax</Typography>
                                        <TextField
                                            sx={{ bgcolor: isConsignedCitySelected === false ? "#F2F2F2" : "#fff" }}
                                            name="tax"
                                            type={"text"}
                                            value={values.tax}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Tax"
                                            disabled={isConsignedCitySelected ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={3} mt={1}>
                                    <Box>
                                        <Typography my={1}>Tax Amount</Typography>
                                        <TextField
                                            sx={{ bgcolor: isConsignedCitySelected === false ? "#F2F2F2" : "#fff" }}
                                            name="taxAmount"
                                            type={"text"}
                                            value={values.taxAmount}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Tax Amount"
                                            disabled={isConsignedCitySelected ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={3} mt={1}>
                                    <Box>
                                        <Typography my={1}>Amount</Typography>
                                        <TextField
                                            sx={{ bgcolor: isConsignedCitySelected === false ? "#F2F2F2" : "#fff" }}
                                            name="amount"
                                            type={"text"}
                                            value={values.amount}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            size="small"
                                            fullWidth
                                            placeholder="Amount"
                                            disabled={isConsignedCitySelected ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                                <Grid2 size={6} mt={1}>
                                    <Box>
                                        <Typography my={1}>Service remarks</Typography>
                                        <TextField
                                            name="description"
                                            id="description"
                                            value={values}
                                            multiline
                                            rows={2}
                                            variant="outlined"
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            sx={{ bgcolor: isConsignedCitySelected === false ? "#F2F2F2" : "#fff" }}
                                            fullWidth
                                            disabled={isConsignedCitySelected ? false : true}
                                        />
                                    </Box>
                                </Grid2>
                            </Grid2>

                            <Grid2 container spacing={3} px={4} pb={3}>
                                <Grid2 size={3}>
                                    <Box mt={3}>
                                        <Typography my={1}>Packing</Typography>

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
                                <Grid2 size={3}>
                                    <Box mt={3}>
                                        <Typography my={1}>Freight</Typography>

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
                                <Grid2 size={3}>
                                    <Box mt={3}>
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
                                    <Grid2 size={4} display={"flex"} alignItems={"end"}>
                                        <Button variant="contained" type="submit" onClick={handleClick}>SAVE INVOICE</Button>
                                        <Button variant="outlined" type="button" sx={{ ml: 2 }} onClick={handleClick}>PRINT</Button>
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
                                        <Box>
                                            <FormControlLabel control={<Checkbox size="small" />} label="Include Challan" />
                                        </Box>
                                    </Grid2>
                                    <Grid2 size={4} >
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
                <AddCustomer handleClose={handleClose} />
            </Modal>
        </Box>
    )
}

export default AddPurchase
