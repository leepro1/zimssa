import { localAxios } from "@/util/http-commons";

const local = localAxios();

const getJjim = async (userId) => {
  try {
    const response = await local.get(`/jjim/${userId}`); // 짬목록 배열의 요소객체의 key는 id, type, location, house_name, user_id;}
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching keyword data:", error);
    throw error;
  }
};

const postJjim = async (userId, location, house_name) => {
  try {
    const response = await local.post(
      `/jjim/detail?userId=${userId}&location=${location}&house_name=${house_name}`
    );
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching detail data:", error);
    return [];
  }
};

const deleteJjim = async (id) => {
  try {
    const response = await local.delete(`/jjim/${id}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching detail data:", error);
    return [];
  }
};

export { getJjim, postJjim, deleteJjim };
