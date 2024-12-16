import { Close } from '@mui/icons-material';
import { Autocomplete, Box, Button, Divider, Grid2, IconButton, Modal, Paper, SelectChangeEvent, TextField, Typography } from '@mui/material'
import { DatePicker } from '@mui/x-date-pickers';
import { Formik, Form } from 'formik';
import CustomReactSelect from '../../components/CustomReactSelect';
import Select from 'react-select/base';
import CustomSelect from '../../components/CustomSelect';
import CustomAutocomplete from '../../components/CustomAutocomplete';
import { useGetStateListQuery } from '../../features/State/StateApi';
import { usePostVendorMutation,useGetBankListQuery, useGetAccountTypesQuery } from '../../features/VendorService/vendorApi';
import MuiAutoComplete from '../../components/MuiAutoComplete';
import { SetStateAction } from 'react';
import { toast } from 'react-toastify';


const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: "70%",
  height: "92%",
  bgcolor: 'background.paper',
  borderRadius: 2,
  boxShadow: 24,
};
const AddVendor = ({ handleClose, handleCloseVendorModal }: any) => {
  const {
    data: states,
    error: statesError,
    isLoading: statesIsLoading,
  } = useGetStateListQuery({});
  const {
    data: bankListdata,
    error: bankListDataError,
    isLoading: bankListDatalsIsLoading,
} = useGetBankListQuery({});
const {
    data: accountTypesData,
    error: accountTypesDataError,
    isLoading: accountTypesDataIsLoading,
} = useGetAccountTypesQuery({});

console.log("banck, account-types" , bankListdata, accountTypesData);

  console.log(states,handleCloseVendorModal);

  const [postVendor, { data: postVendorData, isLoading: postVendorIsLoading, isError: postVendorIsError }] = usePostVendorMutation()


  const initialValues = {
    title: { label: "", value: "" },
    name: "",
    phoneNo: "",
    address1: "",
    address2: "",
    city: "",
    stateId: { label: "", value: "" },
    postCode: "",
    bankId: { label: "", value: "" },
    accountName: "",
    accountNo: "",
    accountType: { label: "", value: "" },
    ifscCode: "",
    bankBranch: "",
    bankAddress: "",
    terms: "",
  }
  const handleSubmit = (values: any) => {
    console.log(values);
    const data = {
      ...values,
      title: values.title.value,
      stateId: values.stateId.value,
      bankId: values.bankId.value,
      accountType: values.accountType.value,
    }
    // postVendor(data).then(res => typeof(res) === "object" ? toast.success("Vendor added successfully") : toast.error("Error on adding vendor"));
    postVendor(data).then((res) => {
      if(typeof(res) === "object"){
        toast.success("Vendor added successfully");
        handleCloseVendorModal()
      } else {
        toast.error("Error on adding vendor")
      }
    })
  }

  // if(typeof(res) === "object"){
  //   toast.success("Vendor added successfully");
  //   handleCloseVendorModal
  // } else {
  //   toast.error("Error on adding vendor")
  // }

  // if(res?.data?.status === 201){
  //   toast.success("Vendor added successfully")
  //   handleCloseVendorModal();
  // }

  console.log(postVendorData);

  const stateOptions = states?.map((state: any) => {
    return { label: state.name, value: state.id }
  })
  const bankOptions = bankListdata?.map((state: any) => {
    return { label: state.name, value: state.id }
  })
  const accountTypeOptions = accountTypesData?.map((state: any) => {
    return { label: state.name, value: state.id }
  })
  // const vendorOptions: Array<{ label: string, value: any }> = [
  //   { label: "one", value: 1 },
  //   { label: "two", value: 2 },
  //   { label: "three", value: 3 }
  // ]
  const martialStatusTitleList = [
    { label: "Mr.", value: "MR" },
    { label: "Ms.", value: "MS" },
    { label: "Mrs.", value: "MRS" }
  ]
  return (
    <Box sx={style}>
      <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", px: 2, bgcolor: "#f44336", color: "#fff", borderRadius: "2px 0px 0px 0px" }}>
        <Typography>Save Vendor</Typography>
        <IconButton>
          <Close sx={{ color: "#fff" }} onClick={handleClose} />
        </IconButton>
      </Box>
      <Formik initialValues={initialValues} onSubmit={handleSubmit}>
        {({ values, setFieldValue, handleChange, handleBlur }) => (
          <Form>
            <Grid2 container px={3} pb={2} columnSpacing={3}>
              <Grid2 size={4}>
                <Box mt={2}>
                  <Typography my={1}>Name</Typography>
                  <Box display={'flex'}>
                    <Autocomplete
                      disablePortal
                      options={martialStatusTitleList}
                      value={values.title}
                      onChange={(event, newValue) => setFieldValue("title", newValue)}
                      sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 1px 2px" }, width: "38%" }}
                      renderInput={(params: any) => <TextField {...params} placeholder="M/S" sx={{ "& .MuiAutocomplete-endAdornment": { padding: 0 } }} />}
                    />
                    <TextField
                      name="name"
                      type={"text"}
                      value={values.name}
                      onBlur={handleBlur}
                      onChange={handleChange}
                      size="small"
                      placeholder="Name"
                      fullWidth
                      sx={{ ml: 1 }}
                    />
                  </Box>
                </Box>
                <Box mt={2}>
                  <Typography my={1}>Phone</Typography>
                  <TextField
                    name="phoneNo"
                    type={"text"}
                    value={values.phoneNo}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    placeholder="Phone"
                    fullWidth
                  />
                </Box>
                <Box mt={2}>
                  <Typography my={1}>Address line 1</Typography>
                  <TextField
                    name="address1"
                    type={"text"}
                    value={values.address1}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    placeholder="Address line 1"
                    fullWidth
                  />
                </Box>
                <Box mt={2}>
                  <Typography my={1}>Address line 2</Typography>
                  <TextField
                    name="address2"
                    type={"text"}
                    value={values.address2}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    placeholder="Address line 2"
                    fullWidth
                  />
                </Box>
                <Box mt={2}>
                  <Typography my={1}>City</Typography>
                  <TextField
                    name="city"
                    type={"text"}
                    value={values.city}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    placeholder="City"
                    fullWidth
                  />
                </Box>
              </Grid2>

              <Grid2 size={4}>
                <Box mt={1}>
                  <Box display={"flex"} justifyContent={"space-between"}>
                    <Typography my={1}>State</Typography>
                  </Box>
                  <Autocomplete
                    disablePortal
                    options={stateOptions}
                    value={values.stateId}
                    onChange={(event, newValue) => setFieldValue("stateId", newValue)}
                    sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 1px 2px" } }}
                    renderInput={(params: any) => <TextField {...params} placeholder="State" />}
                  />
                </Box>
                <Box mt={2}>
                  <Box>
                    <Typography my={1}>Zip</Typography>
                  </Box>
                  <TextField
                    name="postCode"
                    type={"text"}
                    value={values.postCode}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    fullWidth
                    placeholder="Zip"
                  />
                </Box>
                <Box mt={2}>
                  <Box>
                    <Typography my={1}>Bank Name</Typography>
                  </Box>
                  <Autocomplete
                    disablePortal
                    options={bankOptions}
                    value={values.bankId}
                    onChange={(event, newValue) => setFieldValue("bankId", newValue)}
                    sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 1px 2px" } }}
                    renderInput={(params: any) => <TextField {...params} placeholder="Bank Name" />}
                  />
                </Box>
                <Box mt={2}>
                  <Box>
                    <Typography my={1}>Account Name</Typography>
                  </Box>
                  <TextField
                    name="accountName"
                    type={"text"}
                    value={values.accountName}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    fullWidth
                    placeholder="Account name"
                  />
                </Box>
                <Box mt={2}>
                  <Box>
                    <Typography my={1}>Account No</Typography>
                  </Box>
                  <TextField
                    name="accountNo"
                    type={"text"}
                    value={values.accountNo}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    fullWidth
                    placeholder="Account number"
                  />
                </Box>
                {/* <Box>
                  Upload Logo
                </Box> */}
              </Grid2>

              <Grid2 size={4}>

                <Box mt={2}>
                  <Box>
                    <Box>
                      <Typography my={1}>Account Type</Typography>
                    </Box>
                    <Autocomplete
                      disablePortal
                      options={accountTypeOptions}
                      value={values.accountType}
                      onChange={(event, newValue) => setFieldValue("accountType", newValue)}
                      sx={{ "& .MuiOutlinedInput-root": { padding: "1px 2px 1px 2px" } }}
                      renderInput={(params: any) => <TextField {...params} placeholder="Account Type" />}
                    />
                  </Box>
                  <Box mt={2}>
                    <Typography my={1}>IFSC Code</Typography>
                  </Box>
                  <TextField
                    name="ifscCode"
                    type={"text"}
                    value={values.ifscCode}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    fullWidth
                    placeholder="IFSC code"
                  />
                </Box>
                <Box mt={2}>
                  <Box >
                    <Typography my={1}>Bank Branch</Typography>
                  </Box>
                  <TextField
                    name="bankBranch"
                    type={"text"}
                    value={values.bankBranch}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    size="small"
                    fullWidth
                    placeholder="Bank branch"
                  />
                </Box>
                <Box mt={2}>
                  <Typography my={1}>Bank Address</Typography>
                  <TextField
                    name="bankAddress"
                    id="bank_address"
                    value={values.bankAddress}
                    multiline
                    rows={4.2}
                    variant="outlined"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    fullWidth
                    placeholder='Bank address...'
                  />
                </Box>

              </Grid2>
              <Grid2 size={12}>
                <Box mt={2}>
                  <Typography my={1}>Terms</Typography>
                  <TextField
                    name="terms"
                    id="Terms"
                    value={values.terms}
                    multiline
                    rows={2}
                    variant="outlined"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    fullWidth
                    placeholder='Terms...'
                  />
                </Box>
              </Grid2>
            </Grid2>
            <Box display={"flex"} justifyContent={"end"}>
              <Button variant='contained' sx={{ mr: 2 }} type='submit' disableElevation>SAVE VENDOR</Button>
            </Box>
          </Form>
        )}
      </Formik>
    </Box>
  )
}

export default AddVendor