import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function submitFormData(userId, formData, success, fail) {
  

       await local.post(`/junseproduct/junse/${userId}`, formData).then(success).catch(fail);
    


}
async function getJunseInfo(userId, success, fail) {
 
    await local.get(`/junseproduct/junse/list/${userId}`).then(success).catch(fail);
}

export { submitFormData ,getJunseInfo};
